package com.miranda.orderapi.entity;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "order_line")
public class OrderLine {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_line_id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "orden", nullable = false)
	private Order order;
	
	@ManyToOne
	@JoinColumn(name = "product", nullable = false)
	private Product product;
	
	@Column(nullable = false)
	private Double price;
	
	@Column(nullable = false)
	private Double quantity;
	
	@Column(nullable = false)
	private Double total;
	
	public Double getTotal(Double line) {
		Double t = 0d;
		this.price = product.getPrice();
		total = product.getPrice() * line;
		return t;
		
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderLine other = (OrderLine) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "OrderLine [id=" + id  + ", price=" + price + ", quantity=" + quantity
				+ "]";
	}

	/*
	 * @Override public String toString() { return "OrderLine [id=" + id +
	 * ", order=" + order + ", product=" + product + ", price=" + price +
	 * ", quantity=" + quantity + ", total=" + total + "]"; }
	 */
	
	
	
	
	
	
}
