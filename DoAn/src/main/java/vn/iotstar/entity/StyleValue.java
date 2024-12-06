package vn.iotstar.entity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "stylevalue")
public class StyleValue {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "style_value_id")
	private long id;
	private String name;
	@ManyToOne
	@JoinColumn(name = "style_id", nullable = false)
	private Style styleId;
	@ManyToMany(mappedBy = "styleValueIds")
	private List<Product> products = new ArrayList<>();
	private boolean isDelete = false;
	private Date createAt;
	private Date updateAt;
}
