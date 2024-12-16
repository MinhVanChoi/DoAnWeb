package vn.iotstar.services.implement;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import vn.iotstar.entity.CartItem;
import vn.iotstar.entity.CartItemId;
import vn.iotstar.repository.CartItemRepository;
import vn.iotstar.services.CartItemService;

@Service
@Transactional
public class CartItemServiceImp implements CartItemService{
	@Autowired
	CartItemRepository cartItemRepository;
	
    @PersistenceContext
    private EntityManager entityManager;

	@Override
	public <S extends CartItem> S save(S entity) {
		return cartItemRepository.save(entity);
	}

	@Override
	public List<CartItem> findAll(Sort sort) {
		return cartItemRepository.findAll(sort);
	}

	@Override
	public Page<CartItem> findAll(Pageable pageable) {
		return cartItemRepository.findAll(pageable);
	}

	@Override
	public List<CartItem> findAll() {
		return cartItemRepository.findAll();
	}

	@Override
	public Optional<CartItem> findById(CartItemId id) {
		return cartItemRepository.findById(id);
	}

	@Override
	public long count() {
		return cartItemRepository.count();
	}

	@Override
	@Transactional
	public void deleteById(CartItemId id) {
		cartItemRepository.deleteById(id);
	}

	@Override
	public void delete(CartItem entity) {
		System.out.println("thuc hien delete cartitem");
		cartItemRepository.delete(entity);
	}

	 @Override
	public void deleteCartItem(Long cartId, Long productId) {

	        CartItemId cartItemId = new CartItemId(cartId, productId);

	        cartItemRepository.deleteById(cartItemId);

	    }
	 
	  @Override
		@Transactional
		    public void deleteCartItemsByCartId(Long cartId) {
		        // JPQL để xóa các CartItem có cart_id tương ứng
		      String sql = "DELETE FROM cartitem WHERE cart_id = :cartId";
		        entityManager.createNativeQuery(sql)
		                     .setParameter("cartId", cartId)
		                     .executeUpdate();
		        System.out.println("Deleting CartItems with cartId: " + cartId);
		    }
		
	  
	  @Override
	@Transactional
	public void deleteCartItemsByCartIdAndProductId(Long cartId, Long productId){
		   // Xóa các CartItem theo cartId và productId
		    String deleteSql = "DELETE FROM cartitem WHERE cart_id = :cartId AND product_id = :productId";
		    entityManager.createNativeQuery(deleteSql)
		                 .setParameter("cartId", cartId)
		                 .setParameter("productId", productId)
		                 .executeUpdate();
	    }
	
	

	
	
}
