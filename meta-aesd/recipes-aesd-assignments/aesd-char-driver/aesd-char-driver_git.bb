inherit module
inherit update-rc.d

INITSCRIPT_NAME = "aesdchar-start-stop"
INITSCRIPT_PARAMS = "defaults"

SUMMARY = "Build an external Linux kernel module aesdchar"
DESCRIPTION = "${SUMMARY}"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "git://github.com/cu-ecen-aeld/assignments-3-and-later-eduardpo.git;protocol=ssh;branch=master \
          "
PV = "1.0+git${SRCPV}"
SRCREV = "43538ff22e2de8ad81eba55c9b996041fd931c2b"

S = "${WORKDIR}/git/aesd-char-driver"
UNPACKDIR = "${S}"

# The inherit of module.bbclass will automatically name module packages with
# "kernel-module-" prefix as required by the oe-core build environment.

RPROVIDES:${PN} += "kernel-module-aesdchar"

EXTRA_OEMAKE = "KERNELDIR=${STAGING_KERNEL_DIR}"

# Specify the kernel module name
#KERNEL_MODULE_AUTOLOAD = "aesdchar"

do_compile () {
	oe_runmake
}

do_install () {
	install -d ${D}${base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/char
    install -m 0644 aesdchar.ko ${D}${base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/char/

	install -d ${D}${sysconfdir}/init.d
	install -m 0755 ${S}/aesdchar-start-stop ${D}${sysconfdir}/init.d
	install -m 0755 ${S}/aesdchar_load ${D}${sysconfdir}/init.d
	install -m 0755 ${S}/aesdchar_unload ${D}${sysconfdir}/init.d
}

# Provide start up scripts
FILES:${PN} += "${sysconfdir}/init.d/*"