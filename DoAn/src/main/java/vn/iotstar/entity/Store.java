package vn.iotstar.entity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "stores")
public class Store {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "store_id")
	private long id;
	@Column(columnDefinition = "nvarchar(100)", unique = true, nullable = false)
	private String name;
	@Column(columnDefinition = "nvarchar(100)", nullable = false)
	private String bio;
	@Column(unique = true)
	private String slug;
	@Column(columnDefinition = "nvarchar(255)")
	private String address;
	private String images;
	private boolean isActive = false;
	private boolean isOpen = false;
	private boolean isBan = false;
	private float point;
	private float rating;
	private Date createAt;
	private Date updateAt;
    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL)
	private List<User> staffIds;
	@OneToMany(mappedBy = "store", cascade = CascadeType.ALL)
	private List<Product> products = new ArrayList<>();
	@ManyToOne
	@JoinColumn(name = "owner_id", nullable = false)
	private User owner;
	@OneToMany(mappedBy = "store", cascade = CascadeType.ALL)
	private List<UserFollowStore> followByUsers = new ArrayList<>(); 
	@ManyToOne
	@JoinColumn(name = "store_level")
	private StoreLevel storelevel;
	@OneToMany(mappedBy = "store", cascade = CascadeType.ALL)
	private List<Order> orders = new ArrayList<>();
	@OneToMany(mappedBy = "store", cascade = CascadeType.ALL)
	private List<Review> reviews = new ArrayList<>();
	@OneToMany(mappedBy = "store", cascade = CascadeType.ALL)
	private List<Cart> carts = new ArrayList<>();
	@ManyToOne
	@JoinColumn(name = "commission_id")
	private Commission commission;
}
