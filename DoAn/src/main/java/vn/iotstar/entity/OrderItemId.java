package vn.iotstar.entity;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderItemId implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long orderId;
	private long productId;
}
