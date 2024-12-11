package vn.iotstar.entity;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "products")
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_id")
	private long id;
	@Column(columnDefinition = "nvarchar(100)")
	private String name;
	private String slug;
	private String description;
	private String images;
	private float price;
	private float promotionPrice;
	private int sold;
	private boolean isActice = false;
	private boolean isSelling = false;
	private boolean isBan = false;
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date createAt;
	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateAt;
	@ManyToOne
	@JoinColumn(name = "store_id", nullable = false)
	private Store store;
	@ManyToOne
	@JoinColumn(name = "category_id", nullable = false)
	private Category category;
	@ManyToMany
	@JoinTable(
			name = "product_stylevalue",
			joinColumns = @JoinColumn(name = "product_id"),
			inverseJoinColumns = @JoinColumn(name = "style_value_id"))
	private List<StyleValue> styleValueIds = new ArrayList<>();
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
	private List<ReviewProduct> reviews = new ArrayList<>();
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
	private List<OrderItem> orderItems = new ArrayList<>();
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
	private List<CartItem> cartItems = new ArrayList<>();
}	
