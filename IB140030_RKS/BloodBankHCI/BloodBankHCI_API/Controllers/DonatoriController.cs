using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Entity;
using System.Data.Entity.Infrastructure;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using System.Web.Http.Description;
using BloodBankHCI_API.Models;

namespace BloodBankHCI_API.Controllers
{
    public class DonatoriController : ApiController
    {
        private BankaKrviHCIEntities db = new BankaKrviHCIEntities();

        // GET: api/Donatori
        public IQueryable<Donatori> GetDonatori()
        {
            return db.Donatori;
        }

        // GET: api/Donatori/5
        [ResponseType(typeof(Donatori))]
        public IHttpActionResult GetDonatori(int id)
        {
            bsp_Donatori_GetByIdHCI_Result donatori = db.bsp_Donatori_GetByIdHCI(id).FirstOrDefault();
            if (donatori == null)
            {
                return NotFound();
            }

            return Ok(donatori);
        }

        [HttpGet]
       // [Route("api/Donatori/GetByEmail/{email}")]
        public bsp_Donatori_GetByEmailHCI_Result GetDonatoriByEmail (string email)
        {
            bsp_Donatori_GetByEmailHCI_Result donatori = db.bsp_Donatori_GetByEmailHCI(email).FirstOrDefault();
            if (donatori == null)
            {
                return null;
            }

            return donatori;
        }


        [HttpGet]
       // [Route("api/Donatori/GetByKrvnaGrupaIndex/{id}")]
        public IHttpActionResult GetDonatoriByKrvnaGrupaIndex (int id)
        {
           List<bsp_Donatori_GetByKrvnaGrupaIndexHCI_Result> donatori = db.bsp_Donatori_GetByKrvnaGrupaIndexHCI(id).ToList();
            if (donatori == null)
            {
                return NotFound();
            }

            return Ok(donatori);
        }


        // PUT: api/Donatori/5
        [ResponseType(typeof(void))]
        [HttpPut]
       // [Route("api/Donatori/{id}")]
        public IHttpActionResult PutDonatori(int id, Donatori donatori)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != donatori.DonatorId)
            {
                return BadRequest();
            }

            db.bsp_Donatori_UpdateHCI(donatori.DonatorId, donatori.Ime, donatori.Prezime, donatori.Adresa,
                donatori.Email, donatori.DatumRodjenja, donatori.Telefon, donatori.Spol, donatori.KrvnaGrupa,
                donatori.Lozinka, donatori.GradId, donatori.Aktivan);


            return StatusCode(HttpStatusCode.NoContent);
        }




        [HttpPut]
       // [Route("api/Donatori/UpdateSlike/{id}")]
        public IHttpActionResult PutDonatoriSlika(int id, Donatori donatori)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != donatori.DonatorId)
            {
                return BadRequest();
            }

            db.bsp_Donatori_UpdateSlikeHCI(donatori.DonatorId, donatori.Slika);


            return StatusCode(HttpStatusCode.NoContent);
        }

        // POST: api/Donatori
        [ResponseType(typeof(Donatori))]
        public IHttpActionResult PostDonatori(Donatori donatori)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            db.bsp_Donatori_InsertHCI(donatori.Ime, donatori.Prezime, donatori.Adresa,
                donatori.Email, donatori.DatumRodjenja, donatori.Telefon, donatori.Spol, donatori.Lozinka,
                donatori.Aktivan, donatori.GradId, donatori.KrvnaGrupa, donatori.DatumRegistracije);
           

            return CreatedAtRoute("DefaultApi", new { id = donatori.DonatorId }, donatori);
        }

        // DELETE: api/Donatori/5
        [ResponseType(typeof(Donatori))]
        public IHttpActionResult DeleteDonatori(int id)
        {
            Donatori donatori = db.Donatori.Find(id);
            if (donatori == null)
            {
                return NotFound();
            }

            db.Donatori.Remove(donatori);
            db.SaveChanges();

            return Ok(donatori);
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool DonatoriExists(int id)
        {
            return db.Donatori.Count(e => e.DonatorId == id) > 0;
        }
    }
}