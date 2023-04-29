package com.bigdata.omp;

import java.time.LocalDate;

public class Test2 {


    public static void main(String[] args) {
        LocalDate yesterday = LocalDate.now().minusDays(1);

        System.out.println(yesterday);
    }
}
