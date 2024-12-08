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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "storelevel")
public class StoreLevel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "store_level_id")
	private long id;
	private String name;
	@Column(unique = true, nullable = false)
	private int minPoint;
	@Column(nullable = false)
	private float discount;
	private boolean isDelete = false;
	@OneToMany(mappedBy = "storelevel", cascade = CascadeType.ALL)
	private List<Store> stores = new ArrayList<>();
	private Date createAt;
	private Date updateAt;
}
