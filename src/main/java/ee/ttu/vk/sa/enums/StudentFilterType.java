package ee.ttu.vk.sa.enums;

/**
 * Created by vadimstrukov on 2/16/16.
 */
public enum StudentFilterType {
    CODE("Code"), FIRSTNAME("Firstname"), LASTNAME("Lastname"), GROUP("Group");

    private String name;

    StudentFilterType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
