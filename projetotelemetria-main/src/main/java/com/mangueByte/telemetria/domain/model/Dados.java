package com.mangueByte.telemetria.domain.model;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Dados implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int curvaDireita;
    private int curvaEsquerda;
    private int totalCurvas;

    public Dados() {
    }

    public Dados(int curvaDireita, int curvaEsquerda, int totalCurvas) {
        this.curvaDireita = curvaDireita;
        this.curvaEsquerda = curvaEsquerda;
        this.totalCurvas = totalCurvas;
    }

    public int getCurvaDireita() {
        return this.curvaDireita;
    }

    public void setCurvaDireita(int curvaDireita) {
        this.curvaDireita = curvaDireita;
    }

    public int getCurvaEsquerda() {
        return this.curvaEsquerda;
    }

    public void setCurvaEsquerda(int curvaEsquerda) {
        this.curvaEsquerda = curvaEsquerda;
    }

    public int getTotalCurvas() {
        return this.totalCurvas;
    }

    public void setTotalCurvas(int totalCurvas) {
        this.totalCurvas = totalCurvas;
    }

}
