crack is a multi-threaded password cracker that is based off of the crypt() C function
crack takes a crypt() salt encrypted password as input and will try to decrypt and return the original password

input parameters: threads, keysize, target
threads: # of threads
keysize: up to and including what keysize to test for decryption
target: encrypted password to decrypt
