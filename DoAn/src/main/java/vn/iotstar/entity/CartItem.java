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
@Table(name = "cartitem")
public class CartItem {
	@EmbeddedId
	private CartItemId id;
	@ManyToOne
	@MapsId("cartId")
	@JoinColumn(name = "cart_id")
	private Cart cart;
	@ManyToOne
	@MapsId("productId")
	@JoinColumn(name = "productId")
	private Product product;
	private int count;
	private Date createAt;
	private Date updateAt;
}
