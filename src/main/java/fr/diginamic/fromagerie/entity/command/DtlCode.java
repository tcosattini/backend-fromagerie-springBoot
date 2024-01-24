package fr.diginamic.fromagerie.entity.command;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "t_dtlcode")
public class DtlCode {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer codcde;

  private Integer qte;

  private Integer colis;

  private String commentaire;

  public Integer getCodcde() {
    return codcde;
  }

  public void setCodcde(Integer codcde) {
    this.codcde = codcde;
  }

  public Integer getQte() {
    return qte;
  }

  public void setQte(Integer qte) {
    this.qte = qte;
  }

  public Integer getColis() {
    return colis;
  }

  public void setColis(Integer colis) {
    this.colis = colis;
  }

  public String getCommentaire() {
    return commentaire;
  }

  public void setCommentaire(String commentaire) {
    this.commentaire = commentaire;
  }

}
