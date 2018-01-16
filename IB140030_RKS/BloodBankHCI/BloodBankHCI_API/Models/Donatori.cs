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
    using System.Collections.Generic;
    
    public partial class Donatori
    {
        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2214:DoNotCallOverridableMethodsInConstructors")]
        public Donatori()
        {
            this.Donacije = new HashSet<Donacije>();
            this.Obavjestenja = new HashSet<Obavjestenja>();
            this.ZahtjevZaKrv = new HashSet<ZahtjevZaKrv>();
        }
    
        public int DonatorId { get; set; }
        public string Ime { get; set; }
        public string Prezime { get; set; }
        public string Adresa { get; set; }
        public string Email { get; set; }
        public System.DateTime DatumRodjenja { get; set; }
        public string Telefon { get; set; }
        public string Spol { get; set; }
        public bool Aktivan { get; set; }
        public Nullable<int> GradId { get; set; }
        public string KrvnaGrupa { get; set; }
        public Nullable<System.DateTime> DatumZadnjeDonacije { get; set; }
        public System.DateTime DatumRegistracije { get; set; }
        public string Slika { get; set; }
        public string Lozinka { get; set; }
    
        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2227:CollectionPropertiesShouldBeReadOnly")]
        public virtual ICollection<Donacije> Donacije { get; set; }
        public virtual Gradovi Gradovi { get; set; }
        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2227:CollectionPropertiesShouldBeReadOnly")]
        public virtual ICollection<Obavjestenja> Obavjestenja { get; set; }
        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2227:CollectionPropertiesShouldBeReadOnly")]
        public virtual ICollection<ZahtjevZaKrv> ZahtjevZaKrv { get; set; }
    }
}