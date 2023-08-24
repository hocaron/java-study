package com.basestudy.equalAndHashCode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StringTest {

    private String source;
    private String target;

    @BeforeEach
    void set_up() {

        source = "StringTest";
        target = "StringTest";
    }

    @Test
    void equal_sign_method_test() {

        assertThat(source == target).isTrue();
    }

    @Test
    void equal_method_test() {

        assertThat(source.equals(target)).isTrue();
    }

    @Test
    void hash_code_method_test() {

        assertThat(source.hashCode() == target.hashCode()).isTrue();
    }
}
