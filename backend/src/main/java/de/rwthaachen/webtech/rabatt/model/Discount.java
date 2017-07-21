package de.rwthaachen.webtech.rabatt.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.HashCodeBuilder;

@Entity
@Table(name = "discounts")
public class Discount extends AbstractCommonEntity {

    @Column(nullable = false)
    private String title;

    private String link;

    private String category;

    @Column(columnDefinition = "LONGTEXT")
    private String description;

    @Column(name = "valid_from")
    private Date validFrom;

    @Column(name = "valid_until")
    private Date validUntil;

    @Column(columnDefinition = "LONGTEXT")
    private String image;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "creator_id")
    private User creator;

    @Column(name = "vote_temp", columnDefinition = "int(11) default 0")
    private int voteTemp;

    @Column(name = "pos_Vote", columnDefinition = "int(11) default 0")
    private int posVote;

    @Column(name = "neg_Vote", columnDefinition = "int(11) default 0")
    private int negVote;

    public int getVoteTemp() {
        return voteTemp;
    }

    public void setVoteTemp(int voteTemp) {
        this.voteTemp = voteTemp;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public void setValidUntil(Date validUntil) {
        this.validUntil = validUntil;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Date validFrom) {
        this.validFrom = validFrom;
    }

    public Date getValidUntil() {
        return validUntil;
    }

    public void setValidUntill(Date validUntil) {
        this.validUntil = validUntil;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getPosVote() {
        return posVote;
    }

    public void setPosVote(int posVote) {
        this.posVote = posVote;
    }

    public int getNegVote() {
        return negVote;
    }

    public void setNegVote(int negVote) {
        this.negVote = negVote;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(this.title).toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof User))
            return false;
        User other = (User)obj;
        if (getId() != other.getId())
            return false;
        return true;
    }

}
