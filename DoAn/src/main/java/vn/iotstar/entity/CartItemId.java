package vn.iotstar.entity;

import java.util.Objects;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CartItemId {
	private long cartId;
	private long productId;
	
	
	  @Override
	    public boolean equals(Object o) {
	        if (this == o) return true;
	        if (o == null || getClass() != o.getClass()) return false;
	        CartItemId that = (CartItemId) o;
	        return cartId == that.cartId && productId == that.productId;
	    }

	    @Override
	    public int hashCode() {
	        return Objects.hash(cartId, productId);
	    }
}