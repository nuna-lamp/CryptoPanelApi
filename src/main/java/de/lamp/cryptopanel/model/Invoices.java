package de.lamp.cryptopanel.model;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "invoices")
public class Invoices {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private char uuid;
    private String memo;
    private String email;
    private String first_name;
    private String lastName;
    private String status;
    private String return_url;
    private String callback_url;
    private Timestamp expires_at;
    private Timestamp created_at;
    private Timestamp update_at;
    private String seller_name;
    private double amount;
    private String currency;
    private Integer payment_id;
    private String cancel_url;
    private String extra_data;
    private String endpoint;
    private int doi;
    private String ip;
    private Timestamp option_timestamp;
    private String selected_currencies;
    private String endpoint_version;
    private String note;

    public Invoices(int id, char uuid, String memo, String email, String first_name, String last_name,
                    String  status, String return_url, String callback_url, Timestamp expires_at,
                    Timestamp created_at, Timestamp update_at, String seller_name, double amount,
                    String currency, Integer payment_id, String cancel_url, String extra_data, String endpoint,
                    int doi, String ip, Timestamp option_timestamp, String selected_currencies,
                    String endpoint_version, String note) {
        this.id = id;
        this.uuid = uuid;
        this.memo = memo;
        this.email = email;
        this.first_name = first_name;
        this.lastName = last_name;
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

    public Invoices() {

    }

    public void setId(int id) {
        this.id = id;
    }

    public char getUuid() {
        return uuid;
    }

    public void setUuid(char uuid) {
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

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public Timestamp getExpires_at() {
        return expires_at;
    }

    public void setExpires_at(Timestamp expires_at) {
        this.expires_at = expires_at;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Timestamp getUpdate_at() {
        return update_at;
    }

    public void setUpdate_at(Timestamp update_at) {
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

    public Timestamp getOption_timestamp() {
        return option_timestamp;
    }

    public void setOption_timestamp(Timestamp option_timestamp) {
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

    @Override
    public String toString() {
        return "Invoices{" +
                "id=" + id +
                ", uuid=" + uuid +
                ", memo='" + memo + '\'' +
                ", email='" + email + '\'' +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + lastName + '\'' +
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