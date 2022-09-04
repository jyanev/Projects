RLE: run length encoder

rle is a simple lossless compression/decompression algorithm that runs on a sequence of values that repeat many times
rle has 4 input parameters: input filename, output filename, compression length, mode

input filename:      file to be compressed/decompressed
output filename:     resulting file produced by rle
compression length:  length of  byte sequence to encode by (min 1, max 256)
mode:                0 for compression, 1 for decompression
