package net.liplum.attributes;

public class HasNoSuchAttributeException extends RuntimeException {
    public HasNoSuchAttributeException(Attribute attribute) {
        super("Has No [" + attribute.getRegisterName() + "] Attribute");
    }
}
