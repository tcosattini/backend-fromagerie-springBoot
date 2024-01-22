package backFromagerieSpringBoot.entity.command;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "t_entcde")
public class EntCde {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer codcde;

  private Date datcde;

  private Float timbrecli;

  private Float timbrecde;

  @Column(columnDefinition = "TINYINT")
  private Integer Nbcolis;

  private Float cheqcli;

  public Integer getCodcde() {
    return codcde;
  }

  public void setCodcde(Integer codcde) {
    this.codcde = codcde;
  }

  public Date getDatcde() {
    return datcde;
  }

  public void setDatcde(Date datcde) {
    this.datcde = datcde;
  }

  public Float getTimbrecli() {
    return timbrecli;
  }

  public void setTimbrecli(Float timbrecli) {
    this.timbrecli = timbrecli;
  }

  public Float getTimbrecde() {
    return timbrecde;
  }

  public void setTimbrecde(Float timbrecde) {
    this.timbrecde = timbrecde;
  }

  public Float getCheqcli() {
    return cheqcli;
  }

  public void setCheqcli(Float cheqcli) {
    this.cheqcli = cheqcli;
  }

  public Integer getNbcolis() {
    return Nbcolis;
  }

  public void setNbcolis(Integer nbcolis) {
    Nbcolis = nbcolis;
  }

}
