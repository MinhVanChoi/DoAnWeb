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
@Table(name = "orderitem")
public class OrderItem {
	@EmbeddedId
	private OrderItemId id;
	@ManyToOne
	@MapsId("orderId")
	@JoinColumn(name = "order_id")
	private Order order;
	@ManyToOne
	@MapsId("productId")
	@JoinColumn(name = "product_id")
	private Product product;
	private int count;
	private Date createAt;
	private Date updateAt;
}
