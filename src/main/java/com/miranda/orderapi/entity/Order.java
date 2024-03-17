package com.miranda.orderapi.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "orden")
public class Order {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_id")
	private Long id;
	
	@Column(name = "reg_date", nullable = false, updatable = false)
	private LocalDateTime regDate;
	
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<OrderLine> lines;
	
	@Column(nullable = false)
	private Double total;

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	
	@ManyToOne
	@JoinColumn(name ="fk_user", updatable = false)
	private User user;
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", lines=" + lines+ ", total=" + total + "]";
	}

	
	
	

//	@Override
//	public String toString() {
//		return "Order [id=" + id + ", lines=" + lines.toString() + ", total=" + total + "]";
//	}
//	
	
	
	
	

}
