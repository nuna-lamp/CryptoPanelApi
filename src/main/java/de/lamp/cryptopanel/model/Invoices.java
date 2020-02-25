package de.lamp.cryptopanel.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "invoices")
public class Invoices implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String uuid;
    private String memo;
    private String email;
    private String first_name;
    private String last_name;
    @Column(name = "status")
    private String status;
    private String return_url;
    private String callback_url;
    private String expires_at;
    private String created_at;
    private String update_at;
    private String seller_name;
    private double amount;
    private String currency;
    @Column(name = "payment_id")
    private Integer payment_id;
    private String cancel_url;
    private String extra_data;
    private String endpoint;
    private int doi;
    private String ip;
    private String option_timestamp;
    private String selected_currencies;
    private String endpoint_version;
    private String note;

    @OneToMany(
            fetch = FetchType.EAGER
    )
    @JoinColumn(name = "id", referencedColumnName = "payment_id", foreignKey = @javax.persistence.ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
    private List<Invoices_payments> invoices_payments = new ArrayList<>();

    public Invoices() {
    }

    public Invoices(int id, String uuid, String memo, String email, String first_name,
                    String last_name, String status, String return_url, String callback_url,
                    String expires_at, String created_at, String update_at, String seller_name,
                    double amount, String currency, int payment_id, String cancel_url, String extra_data,
                    String endpoint, int doi, String ip, String option_timestamp, String selected_currencies,
                    String endpoint_version, String note) {
        this.id = id;
        this.uuid = uuid;
        this.memo = memo;
        this.email = email;
        this.first_name = first_name;
        this.last_name = last_name;
        this.status = status;
        this.return_url = return_url;
        this.callback_url = callback_url;
        this.expires_at = expires_at;
        this.created_at = created_at;
        this.update_at = update_at;
        this.seller_name = seller_name;
        this.amount = amount;
        this.currency = currency;
        this.payment_id = payment_id;
        this.cancel_url = cancel_url;
        this.extra_data = extra_data;
        this.endpoint = endpoint;
        this.doi = doi;
        this.ip = ip;
        this.option_timestamp = option_timestamp;
        this.selected_currencies = selected_currencies;
        this.endpoint_version = endpoint_version;
        this.note = note;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReturn_url() {
        return return_url;
    }

    public void setReturn_url(String return_url) {
        this.return_url = return_url;
    }

    public String getCallback_url() {
        return callback_url;
    }

    public void setCallback_url(String callback_url) {
        this.callback_url = callback_url;
    }

    public String getExpires_at() {
        return expires_at;
    }

    public void setExpires_at(String expires_at) {
        this.expires_at = expires_at;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdate_at() {
        return update_at;
    }

    public void setUpdate_at(String update_at) {
        this.update_at = update_at;
    }

    public String getSeller_name() {
        return seller_name;
    }

    public void setSeller_name(String seller_name) {
        this.seller_name = seller_name;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Integer getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(Integer payment_id) {
        this.payment_id = payment_id;
    }

    public String getCancel_url() {
        return cancel_url;
    }

    public void setCancel_url(String cancel_url) {
        this.cancel_url = cancel_url;
    }

    public String getExtra_data() {
        return extra_data;
    }

    public void setExtra_data(String extra_data) {
        this.extra_data = extra_data;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public int getDoi() {
        return doi;
    }

    public void setDoi(int doi) {
        this.doi = doi;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getOption_timestamp() {
        return option_timestamp;
    }

    public void setOption_timestamp(String option_timestamp) {
        this.option_timestamp = option_timestamp;
    }

    public String getSelected_currencies() {
        return selected_currencies;
    }

    public void setSelected_currencies(String selected_currencies) {
        this.selected_currencies = selected_currencies;
    }

    public String getEndpoint_version() {
        return endpoint_version;
    }

    public void setEndpoint_version(String endpoint_version) {
        this.endpoint_version = endpoint_version;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public List<Invoices_payments> getInvoices_payments() {
        return invoices_payments;
    }

    public void setInvoices_payments(List<Invoices_payments> invoices_payments) {
        this.invoices_payments = invoices_payments;
    }

    public static String defaultDateFormat =  "yyyy-MM-dd";

    @Override
    public String toString() {
        return "Invoices{" +
                "id=" + id +
                ", uuid=" + uuid +
                ", memo='" + memo + '\'' +
                ", email='" + email + '\'' +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", status='" + status + '\'' +
                ", return_url='" + return_url + '\'' +
                ", callback_url='" + callback_url + '\'' +
                ", expires_at='" + expires_at + '\'' +
                ", created_at='" + created_at + '\'' +
                ", update_at='" + update_at + '\'' +
                ", seller_name='" + seller_name + '\'' +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                ", payment_id=" + payment_id +
                ", cancel_url='" + cancel_url + '\'' +
                ", extra_data='" + extra_data + '\'' +
                ", endpoint='" + endpoint + '\'' +
                ", doi=" + doi +
                ", ip='" + ip + '\'' +
                ", option_timestamp='" + option_timestamp + '\'' +
                ", selected_currencies='" + selected_currencies + '\'' +
                ", endpoint_version='" + endpoint_version + '\'' +
                ", note='" + note + '\'' +
                '}';
    }
}
