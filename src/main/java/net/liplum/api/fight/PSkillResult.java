package net.liplum.api.fight;

public enum PSkillResult {
    Complete(true),
    CancelTrigger(true),
    Fail(false);

    public final boolean succeed;

    PSkillResult(boolean succeed) {
        this.succeed = succeed;
    }
}
