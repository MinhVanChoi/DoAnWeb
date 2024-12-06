package vn.iotstar.entity;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserFollowStoreId implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    private long userId;
    private long storeId;
}
