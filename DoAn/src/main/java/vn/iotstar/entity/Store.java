package vn.iotstar.entity;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
	@Column(unique = true)
	private String slug;
	@Column(columnDefinition = "nvarchar(255)")
	private String address;
    @Column(nullable = false)
    private double latitude;  // Vĩ độ
    @Column(nullable = false)
    private double longitude;  // Kinh độ
	private boolean isOpen = false;
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date createAt;
	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateAt;

	
}
