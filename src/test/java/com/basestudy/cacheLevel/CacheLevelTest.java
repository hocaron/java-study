package com.basestudy.cacheLevel;

public class CacheLevelTest {
    private static final int ARRAY_SIZE = 64 * 1024 * 1024; // 64MB 배열
    private static final int[] STRIDE_SIZES = {1, 2, 4, 8, 16, 32, 64};

    public static void main(String[] args) {
        // 테스트할 배열 생성
        int[] array = new int[ARRAY_SIZE];

        // 배열 초기화
        for (int i = 0; i < ARRAY_SIZE; i++) {
            array[i] = i;
        }

        // 순차 접근 테스트
        System.out.println("=== 순차 접근 테스트 ===");
        testSequentialAccess(array);

        // 랜덤 접근 테스트
        System.out.println("\n=== 랜덤 접근 테스트 ===");
        testRandomAccess(array);

        // 스트라이드 접근 테스트
        System.out.println("\n=== 스트라이드 접근 테스트 ===");
        testStrideAccess(array);
    }

    private static void testSequentialAccess(int[] array) {
        long startTime = System.nanoTime();
        int sum = 0;
        for (int i = 0; i < array.length; i++) {
            sum += array[i];
        }
        long endTime = System.nanoTime();
        System.out.printf("순차 접근 시간: %.2f ms%n", (endTime - startTime) / 1_000_000.0);
    }

    private static void testRandomAccess(int[] array) {
        long startTime = System.nanoTime();
        int sum = 0;
        for (int i = 0; i < array.length; i++) {
            int randomIndex = (int) (Math.random() * array.length);
            sum += array[randomIndex];
        }
        long endTime = System.nanoTime();
        System.out.printf("랜덤 접근 시간: %.2f ms%n", (endTime - startTime) / 1_000_000.0);
    }

    private static void testStrideAccess(int[] array) {
        for (int stride : STRIDE_SIZES) {
            long startTime = System.nanoTime();
            int iterations = array.length / stride; // 실제 접근 횟수 계산
            int sum = 0;
            for (int i = 0; i < array.length; i += stride) {
                sum += array[i];
            }
            long endTime = System.nanoTime();
            double timePerAccess = (endTime - startTime) / (double)iterations; // 접근당 평균 시간 계산
            System.out.printf("스트라이드 %d 접근당 평균 시간: %.2f ns%n",
                stride, timePerAccess);
        }
    }
}
