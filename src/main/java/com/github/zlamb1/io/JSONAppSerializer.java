package com.github.zlamb1.io;

import com.github.zlamb1.Customer;
import com.github.zlamb1.Hash;
import com.github.zlamb1.Seat;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.List;

public class JSONAppSerializer implements IAppSerializer {
    protected String customerOutputFileName = "customers.json";
    protected String seatOutputFileName = "seats.json";

    @Override
    public void serializeCustomers(Collection<Customer> customer) {
        File outputFile = new File(customerOutputFileName);
        if (!outputFile.exists()) {
            try {
                outputFile.createNewFile();
            } catch (IOException exc) {
                exc.printStackTrace();
            }
        }

        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        for (Customer c : customer) {
            JSONObject customerObject = new JSONObject();
            customerObject.put("name", c.getName());
            customerObject.put("privilege", c.getPrivilege());
            customerObject.put("passwordSalt", Base64.getEncoder().encodeToString(c.getHash().salt));
            customerObject.put("passwordHash", Base64.getEncoder().encodeToString(c.getHash().data));
            jsonArray.put(customerObject);
        }

        jsonObject.put("customers", jsonArray);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(customerOutputFileName))) {
            jsonObject.write(writer);
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }

    @Override
    public void serializeSeats(Collection<Seat> seat) {

    }

    @Override
    public Collection<Customer> deserializeCustomers() {
        Collection<Customer> customers = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(customerOutputFileName))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            JSONObject jsonObject = new JSONObject(sb.toString());
            JSONArray customerJSON = jsonObject.getJSONArray("customers");
            if (customerJSON != null) {
                for (int i = 0; i < customerJSON.length(); i++) {
                    JSONObject customerJSONObject = customerJSON.getJSONObject(i);
                    if (customerJSONObject != null) {
                        String name = customerJSONObject.getString("name");
                        int privilege = customerJSONObject.getInt("privilege");
                        Hash hash = new Hash();
                        hash.salt = Base64.getDecoder().decode(customerJSONObject.getString("passwordSalt"));
                        hash.data = Base64.getDecoder().decode(customerJSONObject.getString("passwordHash"));
                        customers.add(new Customer(name, privilege, hash));
                    }
                }
            }
        } catch (FileNotFoundException ignored) {
        } catch (IOException exc) {
            exc.printStackTrace();
        }

        return customers;
    }

    @Override
    public Collection<Seat> deserializeSeats() {
        return List.of();
    }
}
