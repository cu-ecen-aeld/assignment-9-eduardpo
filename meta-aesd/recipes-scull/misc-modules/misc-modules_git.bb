inherit module
inherit update-rc.d

INITSCRIPT_NAME = "mod-init.sh"
INITSCRIPT_PARAMS = "defaults"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "git://git@github.com/cu-ecen-aeld/assignment-7-eduardpo.git;protocol=ssh;branch=main"
SRC_URI += "file://mod-init.sh"

# Modify these as desired
PV = "1.0+git${SRCPV}"
SRCREV = "e4a5aa9e07cf2b4cb42ce934fcda09305765dffd"

S = "${WORKDIR}/git/misc-modules"
UNPACKDIR = "${S}"

# The inherit of module.bbclass will automatically name module packages with
# "kernel-module-" prefix as required by the oe-core build environment.

RPROVIDES:${PN} += "kernel-module-misc-modules"

EXTRA_OEMAKE = "KERNELDIR=${STAGING_KERNEL_DIR}"

# Specify the kernel module name
#KERNEL_MODULE_AUTOLOAD = "misc-modules"

do_compile () {
	oe_runmake
}

do_install () {
	install -d ${D}${base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/char
	install -m 0644 faulty.ko ${D}${base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/char/
	install -m 0644 hello.ko ${D}${base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/char/

	install -d ${D}${sysconfdir}/init.d
	install -m 0755 ${WORKDIR}/mod-init.sh ${D}${sysconfdir}/init.d
	install -m 0755 ${S}/module_load ${D}${sysconfdir}/init.d
	install -m 0755 ${S}/module_unload ${D}${sysconfdir}/init.d
}

# Provide start up scripts
FILES:${PN} += "${sysconfdir}/init.d/*"