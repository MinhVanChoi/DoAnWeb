package vn.iotstar.entity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "commission")
public class Commission {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(columnDefinition = "nvarchar(30)",nullable = false, unique = true)
	private String name;
	@Column(unique = true, nullable = false)
	@Min(0)
	private float cost;
	@Column(nullable = false, columnDefinition = "nvarchar(300)")
	private String description;
	private boolean isDelete;
	private Date createAt;
	private Date updateAt;
	@OneToMany(mappedBy = "commission", cascade = CascadeType.ALL)
	private List<Store> stores = new ArrayList<>();
}
