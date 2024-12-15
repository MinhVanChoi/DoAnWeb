package vn.iotstar.entity;

import java.util.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private long id;
	@Email
	@Column(unique = true)
	private String email;
	@Column(length = 10, unique = true)
	private String phone;
	@Column(unique = true)
	private String id_card;
	@Column(columnDefinition = "nvarchar(50)")
	private String fullname;
	@Column(columnDefinition = "nvarchar(255)")
	private String address;
	@Column(unique = true)
	private String slug;
	private String password;
	private String avatar;
	private boolean isEmailActive = false;
	private boolean isPhoneActive = false;
	private boolean isBan = false;
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date createAt;
	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateAt;
	
    @Column(name = "latitude", nullable = false)
    private double latitude;  // Vĩ độ

    @Column(name = "longitude", nullable = false)
    private double longitude;  // Kinh độ
    
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(
			name = "users_role",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();

	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<UserFollowProduct> followProducts = new ArrayList<>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Order>  orders = new ArrayList<>();

	
	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
	private Cart cart;
    @ManyToOne
    @JoinColumn(name = "store_id", nullable = true)
    private Store store; 
    
}
