package com.quanlihocsinh.service;

import com.quanlihocsinh.model.Score;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class ScoreService {

    /**
     * Tính điểm trung bình và xếp loại học tập
     */
    public void calculateAveragesAndRating(Score s) {
        // Tính averageCA: trung bình các điểm miệng và 15 phút (bỏ null)
        BigDecimal sumCA = BigDecimal.ZERO;
        int countCA = 0;

        if (s.getOralScore1() != null) {
            sumCA = sumCA.add(BigDecimal.valueOf(s.getOralScore1()));
            countCA++;
        }
        if (s.getOralScore2() != null) {
            sumCA = sumCA.add(BigDecimal.valueOf(s.getOralScore2()));
            countCA++;
        }
        if (s.getScore15Minute1() != null) {
            sumCA = sumCA.add(BigDecimal.valueOf(s.getScore15Minute1()));
            countCA++;
        }
        if (s.getScore15Minute2() != null) {
            sumCA = sumCA.add(BigDecimal.valueOf(s.getScore15Minute2()));
            countCA++;
        }

        if (countCA > 0) {
            BigDecimal avgCA = sumCA.divide(BigDecimal.valueOf(countCA), 2, RoundingMode.HALF_UP);
            s.setAverageCA(avgCA.doubleValue());
        } else {
            s.setAverageCA(null);
        }

        // Lấy các điểm khác
        BigDecimal avgCA = s.getAverageCA() != null ? BigDecimal.valueOf(s.getAverageCA()) : null;
        BigDecimal mid = s.getMidtermScore() != null ? BigDecimal.valueOf(s.getMidtermScore()) : null;
        BigDecimal fin = s.getFinalScore() != null ? BigDecimal.valueOf(s.getFinalScore()) : null;

        double weightCA = 0.3, weightMid = 0.3, weightFin = 0.4;

        boolean hasAny = (avgCA != null) || (mid != null) || (fin != null);
        if (!hasAny) {
            s.setAverageScore(null);
            s.setAcademicRating(null);
            return;
        }

        BigDecimal total = BigDecimal.ZERO;
        BigDecimal weightSum = BigDecimal.ZERO;

        if (avgCA != null) {
            total = total.add(avgCA.multiply(BigDecimal.valueOf(weightCA)));
            weightSum = weightSum.add(BigDecimal.valueOf(weightCA));
        }
        if (mid != null) {
            total = total.add(mid.multiply(BigDecimal.valueOf(weightMid)));
            weightSum = weightSum.add(BigDecimal.valueOf(weightMid));
        }
        if (fin != null) {
            total = total.add(fin.multiply(BigDecimal.valueOf(weightFin)));
            weightSum = weightSum.add(BigDecimal.valueOf(weightFin));
        }

        if (weightSum.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal averageScore = total.divide(weightSum, 2, RoundingMode.HALF_UP);
            s.setAverageScore(averageScore.doubleValue());
        } else {
            s.setAverageScore(null);
        }

        // Xếp loại đơn giản theo averageScore
        Double avg = s.getAverageScore();
        if (avg == null) {
            s.setAcademicRating(null);
        } else if (avg >= 8.0) {
            s.setAcademicRating("Giỏi");
        } else if (avg >= 6.5) {
            s.setAcademicRating("Khá");
        } else if (avg >= 5.0) {
            s.setAcademicRating("Trung bình");
        } else {
            s.setAcademicRating("Yếu");
        }
    }
}
