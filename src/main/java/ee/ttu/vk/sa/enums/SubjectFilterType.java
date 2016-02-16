package ee.ttu.vk.sa.enums;

/**
 * Created by vadimstrukov on 2/16/16.
 */
public enum  SubjectFilterType {
    CODE("Code"), NAME("Name"), TEACHER("Teacher");

    private String name;

    SubjectFilterType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
