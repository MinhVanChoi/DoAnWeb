package vn.iotstar.entity;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "styles")
public class Style {
	@Id
	@Column(name = "style_id")
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String name;
    @ManyToMany
    @JoinTable(
        name = "style_category",
        joinColumns = @JoinColumn(name = "style_id"),
        inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> categories = new ArrayList<>();
	private boolean isDelete = false;
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date createAt;
	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateAt;
}
