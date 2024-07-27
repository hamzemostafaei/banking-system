package com.hamze.banking.system.data.access.entity.id;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class AccountTurnoverEntityId implements Serializable {

    @Column(name = "TURNOVER_NUMBER")
    private Long turnoverNumber;

    @Column(name = "TURNOVER_DATE")
    private Date turnoverDate;

    @Column(name = "ENTRY_NUMBER", nullable = false)
    private Integer entryNumber;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AccountTurnoverEntityId that)) return false;
        return Objects.equals(turnoverNumber, that.turnoverNumber) &&
               Objects.equals(turnoverDate, that.turnoverDate) &&
               Objects.equals(entryNumber, that.entryNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(turnoverNumber, turnoverDate, entryNumber);
    }
}
