/**
 * Copyright (c) 2003, 2007 IBM Corporation and others.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   IBM - Initial API and implementation
 */
package org.eclipse.emf.validation.tests;

import java.util.Calendar;
import java.util.Random;

import ordersystem.Account;
import ordersystem.Address;
import ordersystem.Customer;
import ordersystem.InventoryItem;
import ordersystem.OrderSystem;
import ordersystem.OrderSystemFactory;
import ordersystem.Product;
import ordersystem.Warehouse;

/**
 * Utility methods for creating an example Order System model.
 *
 * @author Christian W. Damus (cdamus)
 */
class Example1 {
	private static final OrderSystemFactory factory = OrderSystemFactory.eINSTANCE;

	private static Random random = new Random(1234567890L);

	/**
	 * Cannot be instantiated by clients.
	 */
	private Example1() {
		super();
	}

	/**
	 * Creates an example Order System. The example data are populated into the
	 * supplied <code>orderSystem</code> instance.
	 *
	 * @param orderSystem the example order system to be populated
	 */
	static void create(OrderSystem orderSystem) {
		createCustomer(orderSystem, "Adams", "Alfred", "Ottawa"); //$NON-NLS-3$//$NON-NLS-2$//$NON-NLS-1$
		createCustomer(orderSystem, "Bairstow", "Bob", "Toronto"); //$NON-NLS-3$//$NON-NLS-2$//$NON-NLS-1$
		createCustomer(orderSystem, "Connell", "Charlie", "Montreal"); //$NON-NLS-3$//$NON-NLS-2$//$NON-NLS-1$

		createProduct(orderSystem, "McGregor Happy Feet Socks"); //$NON-NLS-1$
		createProduct(orderSystem, "Arnold Palmer Polo Shirt"); //$NON-NLS-1$
		createProduct(orderSystem, "Wrangler Jeans"); //$NON-NLS-1$

		createWarehouse(orderSystem, "Ottawa"); //$NON-NLS-1$
		createWarehouse(orderSystem, "Toronto"); //$NON-NLS-1$
		createWarehouse(orderSystem, "Montreal"); //$NON-NLS-1$
	}

	private static Product createProduct(OrderSystem os, String name) {
		Product result = factory.createProduct();

		result.setName(name);
		result.setSku(String.valueOf(hashCode(name)) + '-' + (nextInt(100) - 1));
		result.setPrice(nextInt(30));

		os.getProduct().add(result);

		return result;
	}

	private static Warehouse createWarehouse(OrderSystem os, String city) {
		Warehouse result = factory.createWarehouse();

		result.setName(city);
		result.setLocation(createAddress(city));

		int i = 1;
		for (Product next : os.getProduct()) {
			result.getItem().add(createInventoryItem(next, i++));
		}

		os.getWarehouse().add(result);

		return result;
	}

	private static InventoryItem createInventoryItem(Product product, int index) {
		InventoryItem result = factory.createInventoryItem();

		result.setRestockThreshold(15);
		result.setInStock(nextInt(30));
		result.setProduct(product);

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, index * 7);

		result.setNextStockDate(cal.getTime());

		return result;
	}

	private static Customer createCustomer(OrderSystem os, String last, String first, String city) {
		Customer result = factory.createCustomer();

		result.setLastName(last);
		result.setFirstName(first);
		result.getAccount().add(createAccount("VISA", //$NON-NLS-1$
				String.valueOf(hashCode(last)) + '-' + hashCode(first), city));

		os.getCustomer().add(result);

		return result;
	}

	private static Account createAccount(String payment, String number, String city) {
		Account result = factory.createAccount();

		result.setAccountNumber(number);
		result.setPaymentMethod(payment);
		result.setShippingAddress(createAddress(city));
		result.setBillingAddress(createAddress(city));

		return result;
	}

	private static Address createAddress(String city) {
		Address result = factory.createAddress();

		result.setCity(city);
		result.setStreet(city + " St."); //$NON-NLS-1$
		result.setCountry("Canada"); //$NON-NLS-1$
		result.setNumber(String.valueOf(nextInt(1000)));

		return result;
	}

	private static int hashCode(String s) {
		return Math.abs(s.hashCode());
	}

	private static int nextInt(int range) {
		return random.nextInt(range) + 1;
	}
}
