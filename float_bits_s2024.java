public class float_bits_s2024 {

    public static void main(String[] args) {
        IntFloat i = new IntFloat();
        int j;
        int k;

        i.f = 14.0625f; // 1.1100001 * 2^3

        System.out.printf("i: %d\tf: %f\n", i.i, i.f);
        floatBits(i);
        printIEEE754Fields(i);

        k = 4;
        for (i.f = 0, j = k; ; i.f += k) {
            if (i.f == (0x1 << j)) {
                printIEEE754Fields(i);
                j++;
            }
        }
    }

    static class IntFloat {
        int i;
        float f;
    }

    static void floatBits(IntFloat i) {
        int j;

        for (j = 31; j >= 0; j--) {
            System.out.print((i.i >> j) & 0x1);
        }
        System.out.println();
    }

    static void printIEEE754Fields(IntFloat i) {
        // Printing mantissa prints a leading 0 bit as an artifact of the
        // hex output. That highest bit is not actually part of the mantissa.
        System.out.printf("f: %f\ts: %d\tbe: %d\tue: %d\tm: %#x\n", i.f,
                (i.i >> 31) & 0x1, (i.i >> 23) & 0xff, ((i.i >> 23) & 0xff) - 127,
                i.i & 0x7fffff);
    }
}