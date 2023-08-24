package com.basestudy.equalAndHashCode;

import com.basestudy.Member;
import com.basestudy.Member.Job;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MemberTest {

    private Job sourceJob;
    private Job targetJob;

    private Member source;
    private Member target;

    @BeforeEach
    void set_up() {

        sourceJob = Job.of("testName", "testRole");
        targetJob = Job.of("testName", "testRole");

        source = Member.of("test", sourceJob);
        target = Member.of("test", targetJob);
    }

    @Test
    void equal_sign_method_test() {

        assertThat(source == target).isFalse();
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
