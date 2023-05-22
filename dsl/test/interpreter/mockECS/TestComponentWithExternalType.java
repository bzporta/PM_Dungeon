package interpreter.mockECS;

import semanticAnalysis.types.DSLType;
import semanticAnalysis.types.DSLTypeMember;

@DSLType
public class TestComponentWithExternalType {
    @DSLTypeMember private final int member1;
    @DSLTypeMember private final ExternalType memberExternalType;

    public ExternalType getMemberExternalType() {
        return memberExternalType;
    }

    public TestComponentWithExternalType() {
        member1 = 0;
        memberExternalType = null;
    }
}
