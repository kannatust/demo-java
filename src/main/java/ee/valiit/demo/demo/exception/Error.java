package ee.valiit.demo.demo.exception;

public class Error {

    public enum Code {
        NOT_FOUND(Type.NOT_FOUND),
        NO_RESPONSE(Type.NO_RESPONSE);

        private final Type type;

        Code(Type type) {
            this.type = type;
        }

        public Type type() {
            return type;
        }

        public String stringValue() {
            return "error." + this.name();
        }
    }
    public enum Type {
        NOT_FOUND,
        NO_RESPONSE,

    }
}
