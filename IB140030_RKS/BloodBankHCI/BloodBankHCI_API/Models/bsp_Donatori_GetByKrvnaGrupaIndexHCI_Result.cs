//------------------------------------------------------------------------------
// <auto-generated>
//     This code was generated from a template.
//
//     Manual changes to this file may cause unexpected behavior in your application.
//     Manual changes to this file will be overwritten if the code is regenerated.
// </auto-generated>
//------------------------------------------------------------------------------

namespace BloodBankHCI_API.Models
{
    using System;
    
    public partial class bsp_Donatori_GetByKrvnaGrupaIndexHCI_Result
    {
        public int DonatorId { get; set; }
        public string Ime { get; set; }
        public string Prezime { get; set; }
        public string Email { get; set; }
        public bool Aktivan { get; set; }
        public string Lozinka { get; set; }
        public string KrvnaGrupa { get; set; }
        public Nullable<System.DateTime> DatumZadnjeDonacije { get; set; }
        public System.DateTime DatumRegistracije { get; set; }
        public System.DateTime DatumRodjenja { get; set; }
        public string Spol { get; set; }
        public int GradId { get; set; }
        public string Telefon { get; set; }
        public string Slika { get; set; }
        public string Grad { get; set; }
        public string Adresa { get; set; }
    }
}