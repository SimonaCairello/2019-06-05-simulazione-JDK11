package it.polito.tdp.crimes.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.javadocmd.simplelatlng.LatLng;

import it.polito.tdp.crimes.model.CentroDistretto;
import it.polito.tdp.crimes.model.DistrettoNumero;
import it.polito.tdp.crimes.model.Event;

public class EventsDao {
	
	public List<Event> listAllEvents(){
		String sql = "SELECT * FROM events" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Event> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Event(res.getLong("incident_id"),
							res.getInt("offense_code"),
							res.getInt("offense_code_extension"), 
							res.getString("offense_type_id"), 
							res.getString("offense_category_id"),
							res.getTimestamp("reported_date").toLocalDateTime(),
							res.getString("incident_address"),
							res.getDouble("geo_lon"),
							res.getDouble("geo_lat"),
							res.getInt("district_id"),
							res.getInt("precinct_id"), 
							res.getString("neighborhood_id"),
							res.getInt("is_crime"),
							res.getInt("is_traffic")));
				} catch (Throwable t) {
					t.printStackTrace();
					System.out.println(res.getInt("id"));
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<Integer> listYears(){
		String sql = "SELECT DISTINCT YEAR(reported_date) AS year " + 
				"FROM events";
		
		try {
			Connection conn = DBConnect.getConnection() ;
			PreparedStatement st = conn.prepareStatement(sql) ;
			ResultSet res = st.executeQuery() ;
			
			List<Integer> list = new ArrayList<>() ;
			
			while(res.next()) {
				try {
					list.add(res.getInt("year"));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<Integer> listDistrict(){
		String sql = "SELECT DISTINCT district_id AS id " + 
				"FROM EVENTS " +
				"ORDER BY district_id ASC";
		
		try {
			Connection conn = DBConnect.getConnection() ;
			PreparedStatement st = conn.prepareStatement(sql) ;
			ResultSet res = st.executeQuery() ;
			
			List<Integer> list = new ArrayList<>() ;
			
			while(res.next()) {
				try {
					list.add(res.getInt("id"));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}
	}
	
	public Map<Integer, CentroDistretto> getCentroDistrict(Integer year){
		String sql = "SELECT district_id AS id, AVG(geo_lat) AS lat, AVG(geo_lon) AS lon " + 
				"FROM EVENTS " + 
				"WHERE YEAR(reported_date) = ? " + 
				"GROUP BY district_id";
		
		try {
			Connection conn = DBConnect.getConnection() ;
			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, year);
			ResultSet res = st.executeQuery() ;
			
			Map<Integer, CentroDistretto> map = new HashMap<>() ;
			
			while(res.next()) {
				try {
					LatLng centro = new LatLng(res.getDouble("lat"), res.getDouble("lon"));
					map.put(res.getInt("id"), new CentroDistretto(res.getInt("id"), centro));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return map;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Map<Integer, DistrettoNumero> getDistrettoCentrale(Integer year){
		String sql = "SELECT district_id AS id, COUNT(incident_id) AS num " + 
				"FROM EVENTS " + 
				"WHERE YEAR(reported_date) = ? " + 
				"GROUP BY district_id";
		
		try {
			Connection conn = DBConnect.getConnection() ;
			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, year);
			ResultSet res = st.executeQuery() ;
			
			Map<Integer, DistrettoNumero> map = new TreeMap<>() ;
			
			while(res.next()) {
				try {
					map.put(res.getInt("id"), new DistrettoNumero(res.getInt("id"), res.getInt("num")));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return map;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Event> getEventiDelGiorno(Integer year, Integer month, Integer day){
		String sql = "SELECT * " + 
				"FROM EVENTS " + 
				"WHERE YEAR(reported_date) = ? AND MONTH(reported_date) = ? AND DAY(reported_date) = ?";
		
		try {
			Connection conn = DBConnect.getConnection() ;
			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, year);
			st.setInt(2, month);
			st.setInt(3, day);
			ResultSet res = st.executeQuery() ;
			
			List<Event> list = new ArrayList<>() ;
			
			while(res.next()) {
				try {
					list.add(new Event(res.getLong("incident_id"),
							res.getInt("offense_code"),
							res.getInt("offense_code_extension"), 
							res.getString("offense_type_id"), 
							res.getString("offense_category_id"),
							res.getTimestamp("reported_date").toLocalDateTime(),
							res.getString("incident_address"),
							res.getDouble("geo_lon"),
							res.getDouble("geo_lat"),
							res.getInt("district_id"),
							res.getInt("precinct_id"), 
							res.getString("neighborhood_id"),
							res.getInt("is_crime"),
							res.getInt("is_traffic")));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

}
