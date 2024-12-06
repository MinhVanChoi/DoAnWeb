package vn.iotstar.entity;

import java.sql.Date;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "userfollowstore")
public class UserFollowStore {
	@EmbeddedId
	private UserFollowStoreId id;
	@ManyToOne
	@MapsId("userId")
	@JoinColumn(name = "user_id")
	private User user;
	@ManyToOne
	@MapsId("storeId")
	@JoinColumn(name = "store_id")
	private Store store;
	private Date createAt;
	private Date updateAt;
}
