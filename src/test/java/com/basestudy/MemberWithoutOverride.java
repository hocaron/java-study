package com.basestudy;

public class MemberWithoutOverride {

    private String name;

    private JobWithoutOverride jobWithoutOverride;

    public static MemberWithoutOverride of(String name, JobWithoutOverride jobWithoutOverride) {
        return new MemberWithoutOverride(name, jobWithoutOverride);
    }

    protected MemberWithoutOverride(String name, JobWithoutOverride jobWithoutOverride) {
        this.name = name;
        this.jobWithoutOverride = jobWithoutOverride;
    }

    public static class JobWithoutOverride {

        private String name;

        private String role;

        public static JobWithoutOverride of(String name, String role) {
            return new JobWithoutOverride(name, role);
        }

        protected JobWithoutOverride(String name, String role) {
            this.name = name;
            this.role = role;
        }
    }
}
