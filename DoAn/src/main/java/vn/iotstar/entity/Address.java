package vn.iotstar.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "location") 
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_id")
    private long id;

    @Column(name = "name", nullable = false)
    private String name;  // Tên địa điểm

    @Column(name = "latitude", nullable = false)
    private double latitude;  // Vĩ độ

    @Column(name = "longitude", nullable = false)
    private double longitude;  // Kinh độ
    
    
    
    
}
