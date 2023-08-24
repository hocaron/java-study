package com.basestudy.equalAndHashCode;

import com.basestudy.MemberWithoutOverride;
import com.basestudy.MemberWithoutOverride.JobWithoutOverride;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MemberWithoutOverrideTest {

    private JobWithoutOverride sourceJob;
    private JobWithoutOverride targetJob;

    private MemberWithoutOverride source;
    private MemberWithoutOverride target;

    @BeforeEach
    void set_up() {

        sourceJob = JobWithoutOverride.of("testName", "testRole");
        targetJob = JobWithoutOverride.of("testName", "testRole");

        source = MemberWithoutOverride.of("test", sourceJob);
        target = MemberWithoutOverride.of("test", targetJob);
    }

    @Test
    void equal_sign_method_test() {

        assertThat(source == target).isFalse();
    }

    @Test
    void equal_method_test() {

        assertThat(source.equals(target)).isFalse();
    }

    @Test
    void hash_code_method_test() {

        assertThat(source.hashCode() == target.hashCode()).isFalse();
    }
}
