package com.code.fitbase.model;

import androidx.annotation.NonNull;

public class WeightData {
    private final double weight;
    private final double bmi;
    private final double muscleMass;
    private final double basalMetabolicRate;
    private final double waterContent;
    private final double visceralFat;
    private final double boneMineralContent;
    private final double protein;
    private final double score;
    private final double bodyAge;
    private final double bodyFatRate;
    private final double impedance;
    private final double bodyWater;
    private final double skeletalMuscleMass;

    public WeightData(double weight, double bmi, double muscleMass, double basalMetabolicRate, double waterContent, double visceralFat, double boneMineralContent, double protein, double score, double bodyAge, double bodyFatRate, double impedance, double bodyWater, double skeletalMuscleMass) {
        this.weight = weight;
        this.bmi = bmi;
        this.muscleMass = muscleMass;
        this.basalMetabolicRate = basalMetabolicRate;
        this.waterContent = waterContent;
        this.visceralFat = visceralFat;
        this.boneMineralContent = boneMineralContent;
        this.protein = protein;
        this.score = score;
        this.bodyAge = bodyAge;
        this.bodyFatRate = bodyFatRate;
        this.impedance = impedance;
        this.bodyWater = bodyWater;
        this.skeletalMuscleMass = skeletalMuscleMass;
    }

    public double getWeight() {
        return weight;
    }

    public double getBmi() {
        return bmi;
    }

    public double getMuscleMass() {
        return muscleMass;
    }

    public double getBasalMetabolicRate() {
        return basalMetabolicRate;
    }

    public double getWaterContent() {
        return waterContent;
    }

    public double getVisceralFat() {
        return visceralFat;
    }

    public double getBoneMineralContent() {
        return boneMineralContent;
    }

    public double getProtein() {
        return protein;
    }

    public double getScore() {
        return score;
    }

    public double getBodyAge() {
        return bodyAge;
    }

    public double getBodyFatRate() {
        return bodyFatRate;
    }

    public double getImpedance() {
        return impedance;
    }

    public double getBodyWater() {
        return bodyWater;
    }

    public double getSkeletalMuscleMass() {
        return skeletalMuscleMass;
    }

    @NonNull
    @Override
    public String toString() {
        return "WeightData{" +
                "weight=" + weight +
                ", bmi=" + bmi +
                ", muscleMass=" + muscleMass +
                ", basalMetabolicRate=" + basalMetabolicRate +
                ", waterContent=" + waterContent +
                ", visceralFat=" + visceralFat +
                ", boneMineralContent=" + boneMineralContent +
                ", protein=" + protein +
                ", score=" + score +
                ", bodyAge=" + bodyAge +
                ", bodyFatRate=" + bodyFatRate +
                ", impedance=" + impedance +
                ", bodyWater=" + bodyWater +
                ", skeletalMuscleMass=" + skeletalMuscleMass +
                '}';
    }

    public static class Builder {
        private double weight;
        private double bmi;
        private double muscleMass;
        private double basalMetabolicRate;
        private double waterContent;
        private double visceralFat;
        private double boneMineralContent;
        private double protein;
        private double score;
        private double bodyAge;
        private double bodyFatRate;
        private double impedance;
        private double bodyWater;
        private double skeletalMuscleMass;

        public Builder setWeight(double weight) {
            this.weight = weight;
            return this;
        }

        public Builder setBmi(double bmi) {
            this.bmi = bmi;
            return this;
        }

        public Builder setMuscleMass(double muscleMass) {
            this.muscleMass = muscleMass;
            return this;
        }

        public Builder setBasalMetabolicRate(double basalMetabolicRate) {
            this.basalMetabolicRate = basalMetabolicRate;
            return this;
        }

        public Builder setWaterContent(double waterContent) {
            this.waterContent = waterContent;
            return this;
        }

        public Builder setVisceralFat(double visceralFat) {
            this.visceralFat = visceralFat;
            return this;
        }

        public Builder setBoneMineralContent(double boneMineralContent) {
            this.boneMineralContent = boneMineralContent;
            return this;
        }

        public Builder setProtein(double protein) {
            this.protein = protein;
            return this;
        }

        public Builder setScore(double score) {
            this.score = score;
            return this;
        }

        public Builder setBodyAge(double bodyAge) {
            this.bodyAge = bodyAge;
            return this;
        }

        public Builder setBodyFatRate(double bodyFatRate) {
            this.bodyFatRate = bodyFatRate;
            return this;
        }

        public Builder setImpedance(double impedance) {
            this.impedance = impedance;
            return this;
        }

        public Builder setBodyWater(double bodyWater) {
            this.bodyWater = bodyWater;
            return this;
        }

        public Builder setSkeletalMuscleMass(double skeletalMuscleMass) {
            this.skeletalMuscleMass = skeletalMuscleMass;
            return this;
        }

        public WeightData build() {
            return new WeightData(
                    weight,
                    bmi,
                    muscleMass,
                    basalMetabolicRate,
                    waterContent,
                    visceralFat,
                    boneMineralContent,
                    protein,
                    score,
                    bodyAge,
                    bodyFatRate,
                    impedance,
                    bodyWater,
                    skeletalMuscleMass
            );
        }
    }

}
