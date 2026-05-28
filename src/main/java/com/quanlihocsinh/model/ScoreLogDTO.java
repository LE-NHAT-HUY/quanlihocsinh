package com.quanlihocsinh.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ScoreLogDTO {
    private int logID;
    private String teacherName; // Tên giáo viên
    private String studentName; // Tên học sinh
    private String subjectName; // Tên môn
    private String semesterName; // Tên học kỳ
    private String actionType;
    private String changeContent;
    private Date changeDate;

    public ScoreLogDTO() {
    }

    // Getter & Setter
    public int getLogID() {
        return logID;
    }

    public void setLogID(int logID) {
        this.logID = logID;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSemesterName() {
        return semesterName;
    }

    public void setSemesterName(String semesterName) {
        this.semesterName = semesterName;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getChangeContent() {
        return changeContent;
    }

    public void setChangeContent(String changeContent) {
        this.changeContent = changeContent;
    }

    /**
     * Format the JSON changeContent into a user-friendly Vietnamese string.
     * Returns HTML with <br/>
     * separators. If actionType is INSERT, returns "Nhập điểm lần đầu".
     */
    public String getFormattedChangeContent() {
        if (actionType != null && "INSERT".equalsIgnoreCase(actionType)) {
            return "Thêm mới bản ghi điểm";
        }

        if (changeContent == null || changeContent.trim().isEmpty()) {
            return "";
        }

        // Key -> Vietnamese label mapping
        Map<String, String> labels = new HashMap<>();
        labels.put("oral1", "Miệng 1");
        labels.put("oral2", "Miệng 2");
        labels.put("s15_1", "15 Phút 1");
        labels.put("s15_2", "15 Phút 2");
        labels.put("mid", "Giữa kỳ");
        labels.put("fin", "Cuối kỳ");
        labels.put("midterm", "Giữa kỳ");
        labels.put("final", "Cuối kỳ");

        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode root = mapper.readTree(changeContent);

            if (root == null || (root.isObject() && root.size() == 0)) {
                return changeContent;
            }

            if (root.has("type") && "INSERT".equalsIgnoreCase(root.get("type").asText())) {
                return "Thêm mới bản ghi điểm";
            }

            StringBuilder sb = new StringBuilder();
            Iterator<Map.Entry<String, JsonNode>> fields = root.fields();
            boolean first = true;
            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> e = fields.next();
                String key = e.getKey();
                JsonNode node = e.getValue();

                String label = labels.getOrDefault(key, key);

                String formatted;
                if (node != null && node.isArray() && node.size() >= 2) {
                    JsonNode oldNode = node.get(0);
                    JsonNode newNode = node.get(1);
                    String oldVal = (oldNode == null || oldNode.isNull()) ? "Trống" : oldNode.asText();
                    String newVal = (newNode == null || newNode.isNull()) ? "Trống" : newNode.asText();
                    formatted = label + ": " + oldVal + " ➔ " + newVal;
                } else {
                    String val = (node == null || node.isNull()) ? "Trống" : node.asText();
                    formatted = label + ": " + val;
                }

                if (!first)
                    sb.append("<br/>");
                sb.append(formatted);
                first = false;
            }

            return sb.toString();
        } catch (com.fasterxml.jackson.core.JsonProcessingException jpe) {
            return changeContent;
        } catch (Exception ex) {
            // On other errors, fall back to raw content
            return changeContent;
        }
    }

    public Date getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(Date changeDate) {
        this.changeDate = changeDate;
    }
}