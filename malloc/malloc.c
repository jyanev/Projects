////////////////////////
//                    //
//    John Yanev      //
// john.yanev@slu.edu //
//                    //
////////////////////////
#include <stdio.h>
#include <unistd.h>
#include <errno.h>
#include <string.h>

struct node {
        int size;
        int free;
        struct node* next;
        void* start;
};


struct node* head = NULL;

void* malloc(size_t size) {

        if (head == NULL) {

                void* ret;
                size_t pBreak = sysconf(_SC_PAGESIZE);
                while (size > pBreak) {
                        pBreak += sysconf(_SC_PAGESIZE);
                }
                ret = sbrk(pBreak);

                if (ret == (void*) -1) {
                        errno = ENOMEM;
                        return NULL;
                }

                struct node* newAlloc = ret;
                newAlloc->size = size;
                newAlloc->free = 0;
                newAlloc->start = newAlloc + 1;
                head = newAlloc;

                struct node* freeNode = ret + size + sizeof(struct node);
                freeNode->size = pBreak - size - (2 * sizeof(struct node));
                freeNode->free = 1;
                freeNode->start = freeNode + 1;

                head->next = freeNode;
                freeNode->next = NULL;

                return head->start;

        }

        struct node* currentNode = head;

        while (currentNode->next != NULL) {

                if (currentNode->free == 0 || currentNode->size < size + (sizeof(struct node))) {
                        currentNode = currentNode->next;
                        continue;
                }

                int freeSize = currentNode->size;

                currentNode->size = size;
                currentNode->free = 0;

                struct node* freeNode = ((void*) currentNode) + size + sizeof(struct node);
                freeNode->size = freeSize - size - (sizeof(struct node));
                freeNode->free = 1;
                freeNode->start = freeNode + 1;


                if (currentNode->next != NULL) {
                        freeNode->next = currentNode->next;
                }
                else {
                        freeNode->next = NULL;
                }
                currentNode->next = freeNode;
                break;

        }

        if (currentNode->next == NULL && currentNode->free == 1 && currentNode->size > size + (sizeof(struct node))) {

                int freeSize = currentNode->size;

                currentNode->size = size;
                currentNode->free = 0;

                struct node* freeNode = ((void*) currentNode) + size + sizeof(struct node);
                freeNode->size = freeSize - size - (sizeof(struct node));
                freeNode->free = 1;
                freeNode->start = freeNode + 1;

                currentNode->next = freeNode;
                freeNode->next = NULL;

        }


        else if (currentNode->next == NULL) {

                void* ret;
                size_t pBreak = sysconf(_SC_PAGESIZE);
                while (size > pBreak) {
                        pBreak += sysconf(_SC_PAGESIZE);
                }
                ret = sbrk(pBreak);

                if (ret == (void*) -1) {
                        errno = ENOMEM;
                        return NULL;
                }

                currentNode->next = ret;
                currentNode = currentNode->next;

                currentNode->size = size;
                currentNode->free = 0;
                currentNode->start = currentNode + 1;

                struct node* freeNode = ret + size + sizeof(struct node);
                freeNode->size = pBreak - size - (2 * sizeof(struct node));
                freeNode->free = 1;
                freeNode->start = freeNode + 1;

                currentNode->next = freeNode;
                freeNode->next = NULL;

        }

        return currentNode->start;

}

void free(void* ptr) {

        if (ptr == NULL) {
                return;
        }

        struct node* currentNode = head;
        while (currentNode != NULL) {

                if (currentNode->start == ptr) {
                        currentNode->free = 1;
                        break;
                }
                currentNode = currentNode->next;
        }

        while (currentNode->next != NULL && currentNode->next->free == 1) {
                currentNode->size = currentNode->size + currentNode->next->size + sizeof(struct node);
                currentNode->next = currentNode->next->next;
        }



}

void* realloc(void* ptr, size_t size) {

        struct node* currentNode = head;

        size_t oldSize;

        while (currentNode != NULL) {

                if (currentNode->start == ptr) {
                        oldSize = currentNode->size;
                        break;
                }
                currentNode = currentNode->next;
        }

        void* ret = malloc(size);
        if (size > oldSize) {
                memcpy(ret, ptr, oldSize);
        }
        else {
                memcpy(ret, ptr, size);
        }

        free(ptr);

        return ret;

}

void* calloc(size_t nmemb, size_t size) {

        if (nmemb == 0 || size == 0) {
                return NULL;
        }

        if (__builtin_mul_overflow_p(nmemb, size, (int) 0)) {
                return NULL;
        }

        void* ret = malloc(nmemb * size);
        memset(ret, 0, nmemb * size);

        return ret;

}
