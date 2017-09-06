package mobi.puut.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Chaklader on 6/24/17.
 */
@Entity
@Table(name = "wallet_info")
public class WalletInfo {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "code")
    private String code;

    @NotNull
    @Column(name = "address")
    private String address;

    @NotNull
    @Column(name = "currency")
    private String currency;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "wallet_id")
    Set<Status> statuses = new HashSet<>(0);

    public Long getId() {
        return id;
    }

    public WalletInfo(@NotNull String name, @NotNull String address, @NotNull String currency) {
        this.code = name;
        this.address = address;
        this.currency = currency;
    }

    public WalletInfo(@NotNull String name, @NotNull String address) {
        this.code = name;
        this.address = address;
    }

    public WalletInfo() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Set<Status> getStatuses() {
        return statuses;
    }

    public void setStatuses(Set<Status> statuses) {
        this.statuses = statuses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WalletInfo)) return false;

        WalletInfo that = (WalletInfo) o;

        if (!getId().equals(that.getId())) return false;
        if (!getCode().equals(that.getCode())) return false;
        if (!getAddress().equals(that.getAddress())) return false;
        return getCurrency().equals(that.getCurrency());
    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + getCode().hashCode();
        result = 31 * result + getAddress().hashCode();
        result = 31 * result + getCurrency().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "WalletInfo{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", address='" + address + '\'' +
                ", currency='" + currency + '\'' +
                ", statuses=" + statuses +
                '}';
    }

    public String getAllParametersExcludingStatuses() {
        return "WalletInfo{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", address='" + address + '\'' +
                ", currency='" + currency + '\'' +
                '}';
    }
}
