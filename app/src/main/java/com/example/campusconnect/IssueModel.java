package com.example.campusconnect;

public class IssueModel {
    private String issueName, classRoom, block, regdNumber, role;

    public IssueModel() {}

    public String getIssueName() { return issueName; }
    public String getClassRoom() { return classRoom; }
    public String getBlock() { return block; }
    public String getRegdNumber() { return regdNumber; }
    public String getRole() { return role; }

    public void setIssueName(String issueName) { this.issueName = issueName; }
    public void setClassRoom(String classRoom) { this.classRoom = classRoom; }
    public void setBlock(String block) { this.block = block; }
    public void setRegdNumber(String regdNumber) { this.regdNumber = regdNumber; }
    public void setRole(String role) { this.role = role; }
}
