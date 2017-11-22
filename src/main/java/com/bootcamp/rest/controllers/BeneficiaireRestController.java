package com.bootcamp.rest.controllers;

import com.bootcamp.service.crud.BeneficiaireCRUD;
import com.bootcamp.entities.Beneficiaire;
import com.bootcamp.jpa.BeneficiaireRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.sql.SQLException;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * *
 * @author Archange
 */
@Path("/beneficiaire")
@Api(value = "Beneficiaire", description = "web service on beneficiaire")
public class BeneficiaireRestController {

    BeneficiaireRepository beneficiaireRepository = new BeneficiaireRepository("databasePU");

    @GET
    @Path("/list")
    // @Produces(MediaType.APPLICATION_JSON)
    @Produces("application/json")
    @ApiOperation(value = "List of beneficiaire   ")
    public Response getList() throws SQLException {

        List<Beneficiaire> donnees = BeneficiaireCRUD.readAll();
        return Response.status(200).entity(donnees).build();

    }

    @POST
    @Path("/create")
//    @Produces(MediaType.)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Create beneficiaire   ")
    public Response create(Beneficiaire beneficiaire) {
        String output = " Felicitations objet cree avec succes : ";
        try {

            beneficiaireRepository.create(beneficiaire);
            return Response.status(200).entity(output + beneficiaire.getNom()).build();
        } catch (SQLException ex) {
            return Response.status(404).entity("Erreur: Objet non cree").build();
        }
    }

    @PUT
    @Path("/update")
    @ApiOperation(value = "Update of beneficiaire   ")
    public Response update(Beneficiaire beneficiaire) {
        String output = "Operation succesful";
        try {

            beneficiaireRepository.update(beneficiaire);
            return Response.status(200).entity(output + beneficiaire.getNom()).build();
        } catch (SQLException ex) {
            return Response.status(404).entity("Erreur: Objet non cree").build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces("application/json")
    @ApiOperation(value = "Visualise un beneficiaire en donnant son id ")
    public Response getBeneficiaireById(@PathParam("id") Long id) throws SQLException {
        Beneficiaire donnees = BeneficiaireCRUD.read(id);
        try {
            //Verification de l'existance de l'id dans la base de donnee

            if (donnees == null) {
                return Response.status(500).entity("Aucun beneficiaire ne correspond a l'indifiant indique").build();
            } else {
                return Response.status(200).entity(donnees).build();
            }
        } catch (Exception e) {
            return Response.status(401).entity("Veuillez verifier si c'est bien un URL correcte qque vous entrez").build();
        }
    }

    @DELETE
    @Path("/delete/{id}")
    @ApiOperation(value = "Supprime un beneficiare en  donnant son id  ")
    public Response deleteBene(@PathParam("id") Long id) throws SQLException {
        Beneficiaire b = BeneficiaireCRUD.read(id);
        try {
            BeneficiaireCRUD.delete(b);
            return Response.status(202).entity("l'entite est supprime est succes").build();
        } catch (Exception e) {
            return Response.status(404).entity("Erreur de suppression de l'entite").build();
        }
    }
    
    
    

}
