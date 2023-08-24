package com.basestudy;

import java.util.Objects;

public class Member {

    private String name;

    private Job job;

    public static Member of(String name, Job job) {
        return new Member(name, job);
    }

    protected Member(String name, Job job) {
        this.name = name;
        this.job = job;
    }

    public static class Job {

        private String name;

        private String role;

        public static Job of(String name, String role) {
            return new Job(name, role);
        }

        protected Job(String name, String role) {
            this.name = name;
            this.role = role;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Job job = (Job) o;
            return Objects.equals(name, job.name) &&
                    Objects.equals(role, job.role);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, role);
        }

        @Override
        public String toString() {
            return "Job{" +
                    "name='" + name + '\'' +
                    ", role='" + role + '\'' +
                    '}';
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return Objects.equals(name, member.name) &&
                Objects.equals(job, member.job);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, job);
    }

    @Override
    public String toString() {
        return "Member{" +
                "name='" + name + '\'' +
                ", job=" + job +
                '}';
    }
}
