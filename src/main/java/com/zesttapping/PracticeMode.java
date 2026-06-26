package com.zesttapping;

public enum PracticeMode {
    LOW("Low Ping", "Use a tighter, quicker rhythm and keep your strafe compact.", "Low ping: keep the loop fast and compact."),
    MEDIUM("Medium Ping", "Use the standard 3-hit rhythm with steady spacing.", "Medium ping: stay balanced and consistent."),
    HIGH("High Ping", "Delay your tracking slightly and stay patient through the loop.", "High ping: wait a touch longer before re-entering.");

    private final String displayName;
    private final String guidance;
    private final String timingHint;

    PracticeMode(String displayName, String guidance, String timingHint) {
        this.displayName = displayName;
        this.guidance = guidance;
        this.timingHint = timingHint;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getGuidance() {
        return guidance;
    }

    public String getTimingHint() {
        return timingHint;
    }

    public static PracticeMode fromString(String input) {
        if (input == null) {
            return MEDIUM;
        }

        String normalized = input.toLowerCase();
        if ("low".equals(normalized)) {
            return LOW;
        }
        if ("high".equals(normalized)) {
            return HIGH;
        }
        return MEDIUM;
    }
}
